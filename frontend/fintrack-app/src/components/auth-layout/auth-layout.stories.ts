import type { Meta, StoryObj } from '@storybook/angular';
import { AuthLayout } from './auth-layout.component';

const meta: Meta<AuthLayout> = {
  title: 'Components/AuthLayout',
  component: AuthLayout,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<AuthLayout>;

export const Primary: Story = {
  args: {},
};
