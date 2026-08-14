import type { Meta, StoryObj } from '@storybook/angular';
import { SignupPage } from './signup.component';

const meta: Meta<SignupPage> = {
  title: 'Components/SignupPage',
  component: SignupPage,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<SignupPage>;

export const Primary: Story = {
  args: {},
};
