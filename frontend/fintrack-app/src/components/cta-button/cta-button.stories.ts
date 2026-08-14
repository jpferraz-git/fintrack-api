import type { Meta, StoryObj } from '@storybook/angular';
import { CtaButton } from './cta-button.component';

const meta: Meta<CtaButton> = {
  title: 'Components/CtaButton',
  component: CtaButton,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<CtaButton>;

export const Primary: Story = {
  args: {},
};
