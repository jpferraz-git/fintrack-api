import type { Meta, StoryObj } from '@storybook/angular';
import { ForgotPasswordPage } from './forgot-password.component';

const meta: Meta<ForgotPasswordPage> = {
  title: 'Components/ForgotPasswordPage',
  component: ForgotPasswordPage,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<ForgotPasswordPage>;

export const Primary: Story = {
  args: {},
};
